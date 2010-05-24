package org.doxla.domain;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.UnsupportedEncodingException;

import static org.hibernate.search.annotations.Index.*;

@Entity
//@Indexed
public class ApplicationException {
    @Id @GeneratedValue
    private Long identity;
    private String exceptionTrace;
    private String checksum;

    public ApplicationException() {
    }

    public ApplicationException(String exceptionTrace) {
        if(!StringUtils.hasText(exceptionTrace)) throw new IllegalArgumentException("Exception trace must have text");
        this.exceptionTrace = exceptionTrace;
        this.checksum = DigestUtils.md5DigestAsHex(exceptionTraceAsBytes(exceptionTrace));
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

    private byte[] exceptionTraceAsBytes(String exceptionTrace) {
        try{
            return exceptionTrace.getBytes("UTF-8");
        }catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported but required");
        }
    }

}
