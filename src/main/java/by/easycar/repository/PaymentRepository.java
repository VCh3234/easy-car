package by.easycar.repository;

import by.easycar.model.Payment;
import by.easycar.model.user.UserPrivate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUser(UserPrivate userPrivate);
}
