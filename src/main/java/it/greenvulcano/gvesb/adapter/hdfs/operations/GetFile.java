
package it.greenvulcano.gvesb.adapter.hdfs.operations;

import java.io.IOException;

import org.w3c.dom.Node;

import it.greenvulcano.configuration.XMLConfig;
import it.greenvulcano.configuration.XMLConfigException;
import it.greenvulcano.gvesb.buffer.GVBuffer;
import it.greenvulcano.gvesb.virtual.VCLException;
import it.greenvulcano.util.metadata.PropertiesHandler;
import it.greenvulcano.util.metadata.PropertiesHandlerException;

public class GetFile extends BaseOperation {
	private String src = null;
	private String dest = null;
	private boolean delete = false;
	
	@Override
	public void init(Node node) throws XMLConfigException {
		super.init(node);
		
		src = XMLConfig.get(node, "@remote");
		dest = XMLConfig.get(node, "@local");
		delete = XMLConfig.getBoolean(node, "@delete-remote");
	}
	
	@Override
	public GVBuffer perform(GVBuffer gvBuffer) throws PropertiesHandlerException, VCLException {
		super.perform(gvBuffer);
		
		src = PropertiesHandler.expand(src, gvBuffer);
		dest = PropertiesHandler.expand(dest, gvBuffer);
		
		try {
			hdfs.get(src, dest, delete);
		}
		catch (IllegalArgumentException | IOException e) {
			throw new VCLException(e.getMessage(), e);
		}
		return gvBuffer;
	}
}
