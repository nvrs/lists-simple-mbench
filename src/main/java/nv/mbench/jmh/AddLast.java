package nv.mbench.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.util.Collection;
import java.util.List;

/**
 * Created on 26/8/2016.
 */
public class AddLast extends ListBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = INDICES_NO)
    @Warmup(batchSize = INDICES_NO)
    public List<Integer> addLastArrayList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        emptyArrayList.add(rIndex.ar[i]);

        return emptyArrayList;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = INDICES_NO)
    @Warmup(batchSize = INDICES_NO)
    public List<Integer> addLastFastList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        emptyFastList.add(rIndex.ar[i]);

        return emptyFastList;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = INDICES_NO)
    @Warmup(batchSize = INDICES_NO)
    public Collection<Integer> addLastDeque(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        emtpyDeque.addLast(rIndex.ar[i]);

        return emtpyDeque;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Measurement(batchSize = INDICES_NO)
    @Warmup(batchSize = INDICES_NO)
    public List<Integer> addLastLinkedList(RandomIndexArray rIndex) {
        int i = index.getAndIncrement() % INDICES_NO;
        emptyLinkedList.add(rIndex.ar[i]);

        return emptyLinkedList;
    }

    public static void main(String[] args) throws RunnerException {
        start(AddLast.class.getSimpleName());
    }
}
