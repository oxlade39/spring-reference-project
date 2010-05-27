package org.doxla.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ApplicationExceptionOccurrence {
    @Id @GeneratedValue
    private Long identity;

    private Date occurred;

    @ManyToOne
    private ApplicationException exception;

    public ApplicationExceptionOccurrence() {
    }

    ApplicationExceptionOccurrence(ApplicationException exception) {
        this.exception = exception;
        this.occurred = new Date();
    }

    public Date getWhen() {
        return occurred;
    }

    public ApplicationException getException() {
        return exception;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationExceptionOccurrence event = (ApplicationExceptionOccurrence) o;

        if (exception != null ? !exception.equals(event.exception) : event.exception != null) return false;
        if (occurred != null ? !occurred.equals(event.occurred) : event.occurred != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = occurred != null ? occurred.hashCode() : 0;
        result = 31 * result + (exception != null ? exception.hashCode() : 0);
        return result;
    }
}
