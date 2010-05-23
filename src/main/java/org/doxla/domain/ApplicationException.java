package org.doxla.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ApplicationException {
    @Id @GeneratedValue
    private Long identity;
    private String exceptionTrace;
    private String checksum;

    public ApplicationException() {
    }

    public ApplicationException(String exceptionTrace, String checksum) {
        this.exceptionTrace = exceptionTrace;
        this.checksum = checksum;
    }

    public String getExceptionTrace() {
        return exceptionTrace;
    }

    public String getChecksum() {
        return checksum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationException that = (ApplicationException) o;

        if (checksum != null ? !checksum.equals(that.checksum) : that.checksum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return checksum != null ? checksum.hashCode() : 0;
    }
}
