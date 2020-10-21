package union_find

import spock.lang.Specification

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
