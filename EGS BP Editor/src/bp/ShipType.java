package bp;

public enum ShipType {
	HV (0x10),
	SV (0x04), 
	CV (0x08),
	BA (0x02),
	Unknown(0);
	private final int value;
	ShipType(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	// Java: stupid is smart
	public static ShipType get(int value) {
		switch (value) {
		case 0x10: return HV;
		case 0x04: return SV;
		case 0x08: return CV;
		case 0x02: return BA;
		default: return Unknown;
		}
	}
}
