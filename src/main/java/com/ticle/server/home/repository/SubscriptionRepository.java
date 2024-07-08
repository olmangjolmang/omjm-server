package com.ticle.server.home.repository;

import com.ticle.server.home.domain.Subscription;
import com.ticle.server.home.domain.type.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findBySubsDay(Day dayOfWeek);
}
