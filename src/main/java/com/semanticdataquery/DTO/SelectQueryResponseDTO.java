package com.semanticdataquery.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class SelectQueryResponseDTO {
    List<String> headers;
    List<List<String>> data;

    public void addRow(List<String> row) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.add(row);
    }

}
