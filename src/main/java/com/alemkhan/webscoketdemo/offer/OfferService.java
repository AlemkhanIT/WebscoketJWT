package com.alemkhan.webscoketdemo.offer;

import com.alemkhan.webscoketdemo.model.Offer;
import com.alemkhan.webscoketdemo.offer.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public Optional<Offer> getOfferById(Long id) {
        return offerRepository.findById(id);
    }

    public Offer createOffer(Offer offer) {
        return offerRepository.save(offer);
    }
    public boolean existsByIdAndAuthor(Long id, String author) {
        return offerRepository.existsByIdAndAuthor(id, author);
    }

    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }


    public Offer updateOffer(Long id, Offer offer) {
        offer.setId(id);
        return offerRepository.save(offer);
    }
}
