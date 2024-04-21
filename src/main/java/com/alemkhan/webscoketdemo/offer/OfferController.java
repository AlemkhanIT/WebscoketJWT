package com.alemkhan.webscoketdemo.offer;

import com.alemkhan.webscoketdemo.model.Offer;
import com.alemkhan.webscoketdemo.offer.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> offers = offerService.getAllOffers();
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable Long id) {
        return offerService.getOfferById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer, Authentication authentication) {
        String author = authentication.getName();
        offer.setAuthor(author);
        Offer createdOffer = offerService.createOffer(offer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOffer);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id, Authentication authentication) {
        if (!offerService.existsByIdAndAuthor(id, authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        offerService.deleteOffer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable Long id, @RequestBody Offer offer, Authentication authentication) {
        if (!offerService.existsByIdAndAuthor(id, authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        offer.setId(id);
        String author = authentication.getName();
        offer.setAuthor(author);
        Offer updatedOffer = offerService.updateOffer(id, offer);
        return ResponseEntity.ok(updatedOffer);
    }
}
