package xyz.hxwang.su.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.hxwang.su.entity.Model;
import xyz.hxwang.su.payload.ModelPage;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    Page<Model> findModelsByNameContainingIgnoreCase(String name, Pageable pageable);
}
