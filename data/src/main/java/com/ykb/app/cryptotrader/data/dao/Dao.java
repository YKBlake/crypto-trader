package com.ykb.app.cryptotrader.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

sealed class Dao<T, ID> permits AuthorityDao, ParameterDao, RequestUriDao, RoleDao, TradeBotDao, UserDao, ErrorLogDao, TradeBotStateHistoryDao {

    protected final JpaRepository<T, ID> repo;

    public Dao(JpaRepository<T, ID> repo) {
        this.repo = repo;
    }

    public void delete(T entity) {
        repo.delete(entity);
    }

    public void deleteById(ID id) {
        repo.deleteById(id);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public T save(T entity) {
        return repo.save(entity);
    }

    public void saveAll(List<T> list) {
        repo.saveAll(list);
    }

    public Optional<T> findById(ID id) {
        return repo.findById(id);
    }

    public List<T> findAll() {
        return repo.findAll();
    }

    public long count() {
        return repo.count();
    }

    public boolean existsById(ID id) {
        return repo.existsById(id);
    }

}
