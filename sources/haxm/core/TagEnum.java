package haxm.core;
public enum TagEnum{
	INVALID,
	SEND,
	REGISTER_DATACENTER,
	DATACENTERS_INFO_REQUEST,
	DATACENTERS_INFO_RESPONSE,
	DATACENTER_CONFIGURATION_REQUEST,
	DATACENTER_CONFIGURATION_RESPONSE,
	CREATE_VM_WITH_ACK,
	ACK_CREATE_VM, RUN_TASK,
	SUBMIT_TASK, TASK_EXECUTION
}
