package com.github.pozo.upwords;

public class Step {
    private final Coordinate coordinate;
    private final Character character;

    public Step(Coordinate coordinate, Character character) {
        this.coordinate = coordinate;
        this.character = character;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Character getCharacter() {
        return character;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Step step = (Step) o;

        if (coordinate != null ? !coordinate.equals(step.coordinate) : step.coordinate != null) return false;
        return character != null ? character.equals(step.character) : step.character == null;

    }

    @Override
    public int hashCode() {
        int result = coordinate != null ? coordinate.hashCode() : 0;
        result = 31 * result + (character != null ? character.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Step{" +
                "coordinate=" + coordinate +
                ", character=" + character +
                '}';
    }
}
