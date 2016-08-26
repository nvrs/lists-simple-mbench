package nv.mbench.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

/**
 * Created on 26/8/2016.
 */
public class SetRandomIndex extends ListBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public Integer setToRandomIndexArrayList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;

        return preallocatedArrayList.set(rIndex.ar[i], i);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public Integer setToRandomIndexFastList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;

        return preallocatedFastList.set(rIndex.ar[i], i);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public Integer setToRandomIndexLinkedList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;

        return preallocatedLinkedList.set(rIndex.ar[i], i);
    }

    public static void main(String[] args) throws RunnerException {
        start(SetRandomIndex.class.getSimpleName());
    }
}
