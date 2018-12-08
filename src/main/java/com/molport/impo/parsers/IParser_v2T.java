package com.molport.impo.parsers;

import java.util.List;

/**
 *
 * @author raitis
 */
public interface IParser_v2T {

    List<Rec> parse(String fileName, List<PropRec> records);
    
}
