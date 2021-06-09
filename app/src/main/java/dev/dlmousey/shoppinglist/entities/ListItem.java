package dev.dlmousey.shoppinglist.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.*;

import java.io.Serializable;

@Entity(tableName = "list_item_table")
public class ListItem implements Serializable {

    private static final String TAG = ListItem.class.getSimpleName();

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private Integer id;

    @NonNull
    @ColumnInfo(name="name")
    private String name;

    @Nullable
    @ColumnInfo(name="description")
    private String description;

    @Nullable
    @ColumnInfo(name="price")
    private Double price;

    @NonNull
    @ColumnInfo(name="in_basket")
    private Boolean inBasket = false;

    public ListItem(@NonNull String name,
                    @Nullable String description,
                    @NonNull Boolean inBasket,
                    @Nullable Double price) {
        this.name = name;
        this.description = description;
        this.inBasket = inBasket;
        this.price = price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setInBasket(Boolean inBasket) {
        this.inBasket = inBasket;
    }

    public Boolean getInBasket() {
        return this.inBasket;
    }
}
