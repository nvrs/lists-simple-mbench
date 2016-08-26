package nv.mbench.jmh.list;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.util.List;

/**
 * Created on 26/8/2016.
 */
public class AddRandomIndex extends ListBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public List<Integer> addAtRandomIndexArrayList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        preallocatedArrayList.add(rIndex.ar[i], i);

        return preallocatedArrayList;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public List<Integer> addAtRandomIndexFastList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        preallocatedFastList.add(rIndex.ar[i], i);

        return preallocatedFastList;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public List<Integer> addAtRandomIndexLinkedList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        preallocatedLinkedList.add(rIndex.ar[i], i);

        return preallocatedLinkedList;
    }

    public static void main(String[] args) throws RunnerException {
        start(AddRandomIndex.class.getSimpleName());
    }
}
