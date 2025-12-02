package com.pds.pingou.produto.hq.exception;

public class HqNotFoundException extends RuntimeException {
    public HqNotFoundException(Long id) {
        super("HQ não encontrada com id: " + id);
    }
}