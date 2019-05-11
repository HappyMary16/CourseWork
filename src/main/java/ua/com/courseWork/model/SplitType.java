package ua.com.courseWork.model;

public enum SplitType {
    LOW(3), MEDIUM(5), HIGHT(10), HUGE(15);

    private int numberElements;

    SplitType(int numberElements) {
        this.numberElements = numberElements;
    }

    public int getNumberElements() {
        return numberElements;
    }
}
