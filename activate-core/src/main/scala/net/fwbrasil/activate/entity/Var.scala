package net.fwbrasil.activate.entity

import net.fwbrasil.radon.ref.Ref
import net.fwbrasil.activate.ActivateContext

class Var[T]
          (pValueOption: Option[T])
          (implicit m: Manifest[T], 
           val tval: Option[T] => EntityValue[T], 
           override val context: ActivateContext)
           
    extends Ref[T](pValueOption)(context) 
    	with java.io.Serializable {

	def this(pValue: T) (implicit m: Manifest[T], 
           tval: Option[T] => EntityValue[T], 
           context: ActivateContext) = this(Option(pValue))
	
	var name: String = _
	def valueClass = manifest[T].erasure
	var outerEntity: Entity = _
	def outerEntityClass = outerEntity.getClass.asInstanceOf[Class[_ <: Entity]]
	def toEntityPropertyValue(value: Any) = tval(Option(value).asInstanceOf[Option[T]])
	
	override def get = doInitialized {
		super.get
	}

	override def put(value: Option[T]) = doInitialized {
		super.put(value)
	}
	
	override def destroy: Unit = doInitialized {
	    super.destroy
	}
	   
	private[this] def doInitialized[A](f: => A): A = {
		if(outerEntity!=null)outerEntity.initialize
		f
	}
	
	override def toString = name + " -> " + super.toString
}