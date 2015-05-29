/*******************************************************************************
    Machine to Machine Measurement (M3) Framework 
    Copyright(c) 2012 - 2015 Eurecom

    M3 is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.


    M3 is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with M3. The full GNU General Public License is 
   included in this distribution in the file called "COPYING". If not, 
   see <http://www.gnu.org/licenses/>.

  Contact Information
  M3 : gyrard__at__eurecom.fr, bonnet__at__eurecom.fr, karima.boudaoud__at__unice.fr
  
The M3 framework has been designed and implemented during Amelie Gyrard's thesis.
She is a PhD student at Eurecom under the supervision of Prof. Christian Bonnet (Eurecom) and Dr. Karima Boudaoud (I3S-CNRS/University of Nice Sophia Antipolis).
This work is supported by the Com4Innov platform of the Pole SCS and DataTweet (ANR-13-INFR-0008). 
  
  Address      : Eurecom, Campus SophiaTech, 450 Route des Chappes, CS 50193 - 06904 Biot Sophia Antipolis cedex, FRANCE

 *******************************************************************************/
package eurecom.search.knowledge;

import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import eurecom.common.util.ReadFile;
import eurecom.common.util.Var;
import eurecom.sparql.result.ExecuteSparql;
import eurecom.sparql.result.VariableSparql;
/** Search projects using a specific sensor and return ontology URL, rule URL, security mechanisms used, etc.
 * 
 * @author Amelie Gyrard
 *
 */
public class SearchRule {
	/**
	 * A dataset which references 200 ontology-based Internet of Things applications (ontology, rule, security mechanism, sensor used)
	 */
	String swot_dataset; 

	/**
	 * Query the dataset swot with a specific sensor
	 */
	String sparql_query;
	
	/**
	 * rule dataset
	 */
	String rule_dataset;
	
	public SearchRule(String swot_dataset, String sparql_query, String rule_dataset) {
		super();
		this.swot_dataset = swot_dataset;
		this.rule_dataset = rule_dataset;
		this.sparql_query = sparql_query;
	}
	
	public String getRuleSpecificToSensor(String m2mdevice) {
		Model model = ModelFactory.createDefaultModel();
		ReadFile.enrichJenaModelOntologyDataset(model, this.swot_dataset);
		ReadFile.enrichJenaModelOntologyDataset(model, this.rule_dataset);
		ExecuteSparql req = new ExecuteSparql(model, this.sparql_query);
		ArrayList<VariableSparql> var = new ArrayList<VariableSparql>();
		var.add(new VariableSparql("m2mdevice", Var.NS_M3 + m2mdevice, false));
		String resultSparql = req.getSelectResultAsXML(var);
		return resultSparql;
	}
	
}
