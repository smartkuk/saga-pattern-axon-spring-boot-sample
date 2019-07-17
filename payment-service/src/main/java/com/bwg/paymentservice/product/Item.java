package com.bwg.paymentservice.product;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.bwg.domains.ItemType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Item {

  @Id
  @GeneratedValue
  private Long id;

  @Enumerated(EnumType.STRING)
  private ItemType itemType;

  private Boolean enabled;
}
