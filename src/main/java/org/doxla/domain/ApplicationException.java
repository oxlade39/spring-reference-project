package org.doxla.domain;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity @Configurable 
public class ApplicationException {
    @Id @GeneratedValue
    private Long identity;
    private String exceptionTrace;
    private String checksum;
    @OneToMany(cascade = ALL, mappedBy = "exception")
    private Set<ApplicationExceptionOccurrence> occurrences = new HashSet<ApplicationExceptionOccurrence>();

    public ApplicationException() {
    }

    public ApplicationException(String exceptionTrace) {
        if(!StringUtils.hasText(exceptionTrace)) throw new IllegalArgumentException("Exception trace must have text");
        this.exceptionTrace = exceptionTrace;
        this.checksum = DigestUtils.md5Hex(exceptionTraceAsBytes(exceptionTrace));
        addOccurrenceNow();
    }
    
    @Autowired @Transient
    Session session;
    public Long save() {
        return (Long) session.save(this);
    }

    public String getExceptionTrace() {
        return exceptionTrace;
    }

    public String getChecksum() {
        return checksum;
    }

    public void addOccurrenceNow() {
        occurrences.add(new ApplicationExceptionOccurrence(this));
    }

    public Set<ApplicationExceptionOccurrence> getOccurrences() {
        return Collections.unmodifiableSet(occurrences);
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
