package org.doxla.domain;

import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Indexed
public class ApplicationExceptionEvent {
    @Id @GeneratedValue @DocumentId
    private Long identity;

    @Field(index = Index.UN_TOKENIZED, store = Store.YES)
    @DateBridge(resolution = Resolution.DAY)
    private Date occured;

    @ManyToOne(cascade = {CascadeType.ALL})
    @IndexedEmbedded
    private ApplicationException exception;

    public ApplicationExceptionEvent() {
    }

    public ApplicationExceptionEvent(ApplicationException exception) {
        this.exception = exception;
        this.occured = new Date();
    }

    public Date getWhen() {
        return occured;
    }

    public ApplicationException getException() {
        return exception;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationExceptionEvent event = (ApplicationExceptionEvent) o;

        if (exception != null ? !exception.equals(event.exception) : event.exception != null) return false;
        if (occured != null ? !occured.equals(event.occured) : event.occured != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = occured != null ? occured.hashCode() : 0;
        result = 31 * result + (exception != null ? exception.hashCode() : 0);
        return result;
    }
}
