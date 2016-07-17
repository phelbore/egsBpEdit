package bp;

public enum ShipType {
	HV (0x10),
	SV (0x04), 
	CV (0x08),
	BA (0x02);
	private final int value;
	ShipType(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}
