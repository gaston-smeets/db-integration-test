package com.devgs.demo.dbintegrationtest.model;

import lombok.Data;

@Data
public class Book {

    private int id;

    private String title;

    private Author author;

}

