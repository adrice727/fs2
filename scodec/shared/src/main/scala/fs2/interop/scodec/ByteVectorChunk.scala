package fs2
package interop.scodec

import scodec.bits.ByteVector

final class ByteVectorChunk private (val toByteVector: ByteVector) extends Chunk[Byte] {
  def apply(i: Int): Byte =
    toByteVector(i)

  def size: Int =
    toByteVector.size.toInt

  protected def splitAtChunk_(n: Int): (Chunk[Byte], Chunk[Byte]) = {
    val (before,after) = toByteVector.splitAt(n)
    (ByteVectorChunk(before), ByteVectorChunk(after))
  }

  protected def mapStrict[O2](f: Byte => O2): Chunk[O2] =
    Chunk.indexedSeq(toByteVector.toIndexedSeq.map(f))
}

object ByteVectorChunk {
  def apply(bv: ByteVector): ByteVectorChunk = new ByteVectorChunk(bv)
}
