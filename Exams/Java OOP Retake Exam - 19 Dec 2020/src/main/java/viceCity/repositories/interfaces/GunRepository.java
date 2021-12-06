package viceCity.repositories.interfaces;

import viceCity.models.guns.Gun;

import java.util.ArrayList;
import java.util.Collection;

public class GunRepository implements Repository<Gun> {
    private Collection<Gun> guns;

    public GunRepository() {
        this.guns = new ArrayList<>();
    }

    @Override
    public Collection<Gun> getModels() {
        return guns;
    }

    @Override
    public void add(Gun model) {
        Gun foundGun = guns.stream().filter(gun -> gun == model).findFirst().orElse(null);
        if (foundGun == null) {
            guns.add(model);
        }
    }

    @Override
    public boolean remove(Gun model) {
        return guns.remove(model);
    }

    @Override
    public Gun find(String name) {
        return guns.stream().filter(gun -> gun.getName().equals(name)).findFirst().orElse(null);
    }
}