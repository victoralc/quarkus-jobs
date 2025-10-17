package code.batch;

public record Job(
        String name,
        Long executionId,
        String lastStartTime,
        String lastEndTime,
        String status) {

    public static Job empty(String name) {
        return new Job(name, null, null, null, null);
    }

}