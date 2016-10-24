package br.ufrn.fonoweb.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
@ToString(callSuper = false)
public abstract class AbstractBean<T, ID> implements Serializable, Comparable<T> {

    @Id
    @GeneratedValue(generator = "ID_SEQUENCE", strategy = GenerationType.SEQUENCE)
    private ID id;
}
