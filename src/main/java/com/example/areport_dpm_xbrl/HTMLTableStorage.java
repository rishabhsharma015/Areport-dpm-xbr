package com.example.areport_dpm_xbrl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.areport_dpm_xbrl.Error;

public class HTMLTableStorage extends HTMLCommon {


    private String _autoFill = "&nbsp;";
    private boolean _autoGrow = true;
    private ArrayList<List<Map<String, Object>>> _structure = new ArrayList<>();
    private int _rows = 0;
    private int _cols = 0;
    private int _nestLevel = 0;
    private boolean _useTGroups = false;
	
    public HTMLTableStorage(int tabOffset, boolean useTGroups) {
        super(null, tabOffset);
        this._useTGroups = useTGroups;
    }

    public void setUseTGroups(boolean useTGroups) {
        this._useTGroups = useTGroups;
    }

    public boolean getUseTGroups() {
        return this._useTGroups;
    }

    public void setAutoFill(String fill) {
        this._autoFill = fill;
    }

    public String getAutoFill() {
        return this._autoFill;
    }

    public void setAutoGrow(boolean grow) {
        this._autoGrow = grow;
    }

    public boolean getAutoGrow() {
        return this._autoGrow;
    }

    public void setRowCount(int rows) {
        this._rows = rows;
    }

    public void setColCount(int cols) {
        this._cols = cols;
    }

    public int getRowCount() {
        return this._rows;
    }

    // 
    
    public int getColCount(Integer row) {
    	if(row != null) {
    		int count = 0;
    		for (List<Map<String, Object>> cell : _structure) {
				if(cell instanceof List){
					count++;
				}
			}
    		return count;
    	}
    	return this._cols;
    }
    
    
    public void setRowType(int row, String type){
    	for(int counter=0; counter<this._cols; counter++) {
    		this._structure.get(row).get(counter).put("type", type); 
    	}
    }
    
    
    public void setColType(int col, String type) {
    	for (int counter = 0; counter < this._rows; counter++) {
			this._structure.get(counter).get(col).put("type", type);
		}
    }
    
    
    public Error setCellAttributes(int row, int col, Object attributes) {
    	if((this._structure.get(row).get(col) == null) && !this._structure.get(row).get(col).equals("__SPANNED__")) {
    		Map<String, Object> parsedAttributes = this._parseAttributes(attributes);
            Error err = this._adjustEnds(row, col, "setCellAttributes", parsedAttributes);
            if (Error.isError(err)) {
                return err;
            }
            this._structure.get(row).get(col).put("attr", parsedAttributes);
            this._updateSpanGrid(row, col);
    	}
    }
    
}


