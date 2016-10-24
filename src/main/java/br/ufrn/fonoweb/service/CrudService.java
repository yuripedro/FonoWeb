/*
 * Copyright 2016 JoinFaces.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.ufrn.fonoweb.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author yuri
 * @param <T>
 * @param <ID>
 */
@Transactional(readOnly = true)
public class CrudService<T extends Object, ID extends Serializable> {

    private CrudRepository<T, ID> repository;

    @Inject
    public void setRepository(CrudRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Transactional
    public <S extends T> Iterable<S> save(Iterable<S> objetos) {
        return repository.save(objetos);
    }

    @Transactional
    public <S extends T> S save(S objeto) {
        return repository.save(objeto);
    }

    @Transactional
    public void delete(Iterable<? extends T> objetos) {
        repository.delete(objetos);
    }

    @Transactional
    public void delete(ID id) {
        repository.delete(id);
    }

    @Transactional
    public void delete(T objeto) {
        repository.delete(objeto);
    }

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    public T findOne(ID id) {
        return repository.findOne(id);
    }

    public List<T> findAll() {
        List<T> result = new ArrayList();
        for (T next : repository.findAll()) {
            result.add(next);
        }
        return result;
    }

    public Iterable<T> findAll(Iterable<ID> ids) {
        return repository.findAll(ids);
    }
    public List<T> findAll(List<ID> ids) {
        List<T> result = new ArrayList();
        for (T next : repository.findAll(ids)) {
            result.add(next);
        }
        return result;
    }

    public long count() {
        return repository.count();
    }

    public boolean exists(ID id) {
        return repository.exists(id);
    }
}