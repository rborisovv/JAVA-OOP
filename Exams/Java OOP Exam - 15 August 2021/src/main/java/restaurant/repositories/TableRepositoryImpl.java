package restaurant.repositories;

import restaurant.entities.tables.interfaces.Table;
import restaurant.repositories.interfaces.TableRepository;

import java.util.ArrayList;
import java.util.Collection;

public class TableRepositoryImpl implements TableRepository<Table> {
    private Collection<Table> tables;

    public TableRepositoryImpl() {
        this.tables = new ArrayList<>();
    }

    @Override
    public Collection<Table> getAllEntities() {
        return tables;
    }

    @Override
    public void add(Table entity) {
        this.tables.add(entity);
    }

    @Override
    public Table byNumber(int number) {
        return this.tables.stream()
                .filter(table -> table.getTableNumber() == number)
                .findFirst()
                .orElse(null);
    }
}
