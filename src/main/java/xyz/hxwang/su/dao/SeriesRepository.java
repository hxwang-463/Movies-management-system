package xyz.hxwang.su.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.hxwang.su.entity.Series;

public interface SeriesRepository extends JpaRepository<Series, Long> {
}
