#include "XAccount.h"
#include "json/XJson.h"
#include <map>

static std::string s_empty;
static std::map<std::string, std::string> s_otherInfo;

Account::Account()
{
    m_delegate = nullptr;
}

Account::~Account(){
}

void Account::prepare()
{

}

bool Account::isDefault()
{
	return true;
}

bool Account::hasUserCenter()
{
	return false;
}

const char* Account::getName()
{
	return s_empty.c_str();
}

const char* Account::getId()
{
	return s_empty.c_str();
}

const char* Account::getSessionId()
{
	return s_empty.c_str();
}

const char* Account::getGender()
{
	return s_empty.c_str();
}

const char* Account::getFirstName()
{
	return s_empty.c_str();
}

const char* Account::getLastName()
{
	return s_empty.c_str();
}

const char* Account::getLocale()
{
	return s_empty.c_str();
}

const char* Account::getEmail()
{
	return s_empty.c_str();
}

const char* Account::getProfileImage()
{
	return s_empty.c_str();
}

const char* Account::getChannelId()
{
	return s_empty.c_str();
}

const char* Account::getChannelName()
{
	return s_empty.c_str();
}
	
const char* Account::getAppId()
{
	return s_empty.c_str();
}

const char* Account::getAppKey()
{
	return s_empty.c_str();
}

void Account::clean()
{
}

void Account::login()
{
	if (m_delegate)
		m_delegate->didLoginFinished(nullptr);
}

void Account::logout()
{
	if (m_delegate)
		m_delegate->didLogoutFinished(LogoutFromManual);
}

void Account::swtichAccount()
{

}

void Account::gotoUserCetner()
{

}

void Account::gotoBBS()
{

}

void Account::gotoEnterAPPCetner()
{

}

void Account::showToolbar(bool visible)
{
}

void Account::showToolbar(bool visible, AccountToolBarPlace place)
{
  
}

const char* Account::getOtherInfo(const char* key)
{
	if (nullptr == key)
		return s_empty.c_str();
	auto target = s_otherInfo.find(key);
	if (target != s_otherInfo.end())
		return target->second.c_str();
	else
		return s_empty.c_str();
}

void Account::setOtherInfo(const char* key, const char* value)
{
	if (key && value)
		s_otherInfo.insert(std::make_pair(key, value));
}

void Account::callFuntionBegin()
{

}

void Account::addFunctionParam(const char* key, const char* value)
{

}

void Account::callFunction(const char* name)
{

}

void Account::callFunctionEnd()
{
}

void Account::setDefaultAccountSDKByClassName(const char* className)
{

}

void Account::setSyncParam(const char* key, const char* value)
{
}

const char* Account::getSyncParam(const char* key)
{
	return s_empty.c_str();
}

std::map<std::string, std::string> Account::getAllSyncParam()
{
	std::map<std::string, std::string> allSync;
	return allSync;
}

void Account::cleanSyncParam()
{
}

void Account::setDelegate(AccountDelegate* _delegate)
{
    AccountDelegate* oldDelegate = m_delegate;
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

AccountDelegate* Account::getDelegate()
{
    return m_delegate;
}
