package bp;

public class EPBFile {
	private BlockData blocks;
	private MetaData meta;
	
	public EPBFile(BlockData b, MetaData m) {
		blocks = b;
		meta = m;
	}
	
	public EPBFile(byte[] dataBuf, byte[] metaBuf) {
		meta = new MetaData();
		blocks = new BlockData();

		meta.populate(metaBuf);
		blocks.populate(dataBuf);
	}

	public ZipFile getZip() {
		return new ZipFile(meta, blocks);
	}

}
