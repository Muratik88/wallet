package com.murat.walletapp.controller;

import com.murat.walletapp.entity.Wallet;
import com.murat.walletapp.service.ValidationErrorService;
import com.murat.walletapp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private ValidationErrorService validationErrorService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(walletService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAll(@PathVariable Long id){
        return new ResponseEntity<>(walletService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Wallet wallet, BindingResult result){
        ResponseEntity errors = validationErrorService.validate(result);
        if (errors != null) return errors;

        Wallet walletSave = walletService.createOrUpdate(wallet);
        return new ResponseEntity<Wallet>(walletSave, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Wallet wallet, BindingResult result){
        ResponseEntity errors = validationErrorService.validate(result);
        if (errors != null) return errors;
        wallet.setId(id);

        Wallet walletSave = walletService.createOrUpdate(wallet);
        return new ResponseEntity<Wallet>(walletSave, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        walletService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
