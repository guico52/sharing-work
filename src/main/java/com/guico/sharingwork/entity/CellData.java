package com.guico.sharingwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CellData{
    Integer r;
    Integer c;
    Object v;
}
