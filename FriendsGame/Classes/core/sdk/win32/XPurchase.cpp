#include "XPurchase.h"

NS_X_BEGIN

static std::string s_empty;

Purchase::Purchase()
{
    m_delegate = nullptr;
}

Purchase::~Purchase()
{   
}

void Purchase::prepare()
{
}

bool Purchase::isDefault()
{
	return true;
}

void Purchase::startPurchase()
{
}

void Purchase::setOrderSerial(const char* orderSerial)
{
}

void Purchase::setPrice(float price)
{
}

void Purchase::setPayId(const char* payId)
{
}

void Purchase::setGameUserServer(const char* gameUserServer)
{
}

void Purchase::setName(const char* name)
{
}

void Purchase::setGameUserId(const char* userId)
{
}

void Purchase::setGameUserName(const char* userName)
{
}

void Purchase::setPayUrl(const char* payUrl)
{
}

const char* Purchase::getOtherInfo(const char* key)
{
    return s_empty.c_str();
}

void Purchase::setOtherInfo(const char* key, const char* value)
{
}

void Purchase::callFuntionBegin()
{
}

void Purchase::addFunctionParam(const char* key, const char* value)
{
}

void Purchase::callFunction(const char* name)
{
}

void Purchase::callFunctionEnd()
{
}

void Purchase::setDefaultPurchaseClass(const char* className)
{
}

void Purchase::setSyncParam(const char* key, const char* value)
{
}

const char* Purchase::getSyncParam(const char* key)
{
	return s_empty.c_str();
}

std::map<std::string, std::string> Purchase::getAllSyncParam()
{
	std::map<std::string, std::string> allSync;
	return allSync;
}

void Purchase::cleanSyncParam()
{
}

PurchaseDelegate* Purchase::getDelegate()
{
    return m_delegate;
}

void Purchase::setDelegate(PurchaseDelegate* _delegate)
{
    PurchaseDelegate* oldDelegate = m_delegate;
    m_delegate = _delegate;
    if (m_delegate){
        Ref* obj =	dynamic_cast<Ref*>(m_delegate);
        if (obj)
        obj->retain();
    }
    
    if (oldDelegate){
        Ref* obj = dynamic_cast<Ref*>(m_delegate);
        if (obj)
        obj->release();
        else
        delete oldDelegate;
    }
}

void Purchase::setProductType(const char* szProductType)
{

}

NS_X_END