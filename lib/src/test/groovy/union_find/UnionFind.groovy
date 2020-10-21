package union_find

import spock.lang.Specification
import spock.lang.Timeout

class UnionFindTest extends Specification {

  def uf = new UnionFind(4)

  def "all disjoint"() {
    expect:
    uf.find(0) == 0
    uf.find(1) == 1
    uf.find(2) == 2
    uf.find(3) == 3
  }

  def "one union"() {
    when:
    uf.union(1, 2)

    then:
    uf.find(0) == 0
    uf.find(3) == 3
    uf.find(1) in [1, 2]
    uf.find(1) == uf.find(2)
  }

  def "two unions, version 1"() {
    when:
    uf.union(1, 2)
    uf.union(1, 3)

    then:
    uf.find(0) == 0
    uf.find(1) in [1, 2, 3]
    uf.find(1) == uf.find(2)
    uf.find(1) == uf.find(3)
  }

  def "two unions, version 2"() {
    when:
    uf.union(1, 2)
    uf.union(2, 3)

    then:
    uf.find(0) == 0
    uf.find(1) in [1, 2, 3]
    uf.find(1) == uf.find(2)
    uf.find(1) == uf.find(3)
  }

  def "three unions"() {
    when:
    uf.union(1, 2)
    uf.union(2, 3)
    uf.union(1, 0)

    then:
    uf.find(0) in [0, 1, 2, 3]
    uf.find(0) == uf.find(1)
    uf.find(0) == uf.find(2)
    uf.find(0) == uf.find(3)
  }
}

class UnionFindPerformanceTest extends Specification {
  static final E = 20
  static final N = 1 << E

  def uf = new UnionFind(N)

  @Timeout(1)
  def "performance test, linear"() {
    when:
    for (int i = 1 ; i < N; ++i)
      uf.union(i-1, i)

    then:
    (1..N-1).every({ i -> uf.find(i) == uf.find(0) })
  }

  @Timeout(1)
  def "performance test, balanced"() {
    when:
    for (int p = 0; p < E; ++p)
      for (int i = 0; i < N; i += (2 << p))
        uf.union(i, i + (1 << p))

    then:
    (1..N-1).every({ i -> uf.find(i) == uf.find(0) })
  }
}
