package thebetweenlands.common.network.base;

import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class Packet implements IPacket {
	private MessageContext ctx;

	@Override
	public void setContext(MessageContext ctx) {
		this.ctx = ctx;
	}

	public MessageContext getContext() {
		return this.ctx;
	}
}
