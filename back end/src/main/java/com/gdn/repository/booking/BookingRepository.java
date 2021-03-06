package com.gdn.repository.booking;

import com.gdn.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Dias on 4/8/2017.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, String>, BookingRepositoryCustom {
}
