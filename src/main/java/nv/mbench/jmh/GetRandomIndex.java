package nv.mbench.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

/**
 * Created on 26/8/2016.
 */
public class GetRandomIndex extends ListBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public Integer getFromRandomIndexArrayList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;

        return preallocatedArrayList.get(rIndex.ar[i]);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public Integer getFromRandomIndexFastList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;

        return preallocatedFastList.get(rIndex.ar[i]);
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public Integer getFromRandomIndexLinkedList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;

        return preallocatedLinkedList.get(rIndex.ar[i]);
    }

    public static void main(String[] args) throws RunnerException {
        start(GetRandomIndex.class.getSimpleName());
    }
}
