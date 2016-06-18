#include "XSDKCenter.h"

SDKCenter* SDKCenter::s_instance = nullptr;

SDKCenter* SDKCenter::getInstance()
{
    if(nullptr == s_instance)
	{
        s_instance = new SDKCenter();
    }
    return s_instance;
}

SDKCenter::SDKCenter()
{
    m_account = new Account();
    m_purchase = new Purchase();
    m_share = new Share();
}

SDKCenter::~SDKCenter()
{
}

Account* SDKCenter::getAccount()
{
	SDKCenter* center = SDKCenter::getInstance();
    return center->m_account;
}

Purchase* SDKCenter::getPurchase()
{
	SDKCenter* center = SDKCenter::getInstance();
    return center->m_purchase;
}

Share* SDKCenter::getShare()
{
    SDKCenter* center = SDKCenter::getInstance();
    return center->m_share;
}
