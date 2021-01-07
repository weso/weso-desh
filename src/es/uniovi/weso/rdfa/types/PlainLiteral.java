package es.uniovi.weso.rdfa.types;

import com.hp.hpl.jena.datatypes.BaseDatatype;
import com.hp.hpl.jena.datatypes.DatatypeFormatException;
import com.hp.hpl.jena.graph.impl.LiteralLabel;

public class PlainLiteral extends BaseDatatype {
	
	public static final String TYPE_URI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral";
	public static final PlainLiteral PLAIN_LITERAL_TYPE = new PlainLiteral();
	
	public PlainLiteral() {
		super(TYPE_URI);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isEqual(LiteralLabel value1, LiteralLabel value2) {
		// TODO Auto-generated method stub
		return super.isEqual(value1, value2);
	}

	@Override
	public Object parse(String lexicalForm) throws DatatypeFormatException {
		// TODO Auto-generated method stub
		return super.parse(lexicalForm);
	}

	@Override
	public String unparse(Object value) {
		// TODO Auto-generated method stub
		return super.unparse(value);
	}
	
}
