package nv.mbench.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.util.Collection;
import java.util.List;

/**
 * Created on 26/8/2016.
 */
public class AddFirst extends ListBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public List<Integer> addFirstArrayList(ListBenchmark.RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        preallocatedArrayList.add(0, rIndex.ar[i]);

        return preallocatedArrayList;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public List<Integer> addFirstFastList(ListBenchmark.RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        preallocatedFastList.add(0, rIndex.ar[i]);

        return preallocatedFastList;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public Collection<Integer> addFirstDeque(ListBenchmark.RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        preallocatedDeque.addFirst(rIndex.ar[i]);

        return preallocatedDeque;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = 10000)
    @Warmup(batchSize = 1000)
    public List<Integer> addFirstLinkedList(ListBenchmark.RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        preallocatedLinkedList.add(0, rIndex.ar[i]);

        return preallocatedLinkedList;
    }

    public static void main(String[] args) throws RunnerException {
        start(AddFirst.class.getSimpleName());
    }
}
