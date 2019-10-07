package by.attrade.service;

import by.attrade.domain.VerificationToken;
import by.attrade.repos.VerificationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationTokenService {
    private final VerificationTokenRepo verificationTokenRepo;

    @Autowired
    public VerificationTokenService(VerificationTokenRepo verificationTokenRepo) {
        this.verificationTokenRepo = verificationTokenRepo;
    }

    public void save(VerificationToken token) {
        verificationTokenRepo.save(token);
    }

    public Optional<VerificationToken> findById(String id) {
        return verificationTokenRepo.findById(id);
    }

    public void deleteById(String id) {
        verificationTokenRepo.deleteById(id);
    }
}
