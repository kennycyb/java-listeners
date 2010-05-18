package com.wpl.common.event;

import java.io.Serializable;

/**
 * The generic event arguments. Declare as serializable, so that in future, this can be send over
 * network, or store in database, files, etc.
 */
public interface IEventArgs extends Serializable {

}
