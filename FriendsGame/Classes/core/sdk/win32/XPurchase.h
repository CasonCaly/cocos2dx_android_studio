#ifndef X_PURCHASE_H
#define X_PURCHASE_H
#include <string>
#include <map>
using namespace std;
#include "cocos2d.h"
using namespace cocos2d;

class  PurchaseDelegate{
    
public:
    
    virtual void didPurchaseFinish(const char* error){}
    
    virtual ~PurchaseDelegate(){}
};

class  Purchase : public Ref{
public:

	Purchase();

	~Purchase();

	void prepare();

	bool isDefault();

	void startPurchase();

	void setOrderSerial(const char* orderSerial);

	void setPrice(float price);

	void setPayId(const char* payId);

	void setGameUserServer(const char* gameUserServer);

	void setName(const char* name);

	void setGameUserId(const char* userId);

	void setGameUserName(const char* userName);

	void setPayUrl(const char* payUrl);

    void setDefaultPurchaseClass(const char* className);
    
	void setProductType(const char* szProductType);

public:
    
    const char* getOtherInfo(const char* key);
    
    void setOtherInfo(const char* key, const char* value);
    
    void callFuntionBegin();
    
    void addFunctionParam(const char* key, const char* value);
    
    void callFunction(const char* name);
    
    void callFunctionEnd();

public:

	void setSyncParam(const char* key, const char* value);

	const char* getSyncParam(const char* key);

	std::map<std::string, std::string> getAllSyncParam();

	void cleanSyncParam();

public:
    
    void setDelegate(PurchaseDelegate* _delegate);
    
    PurchaseDelegate* getDelegate();
    
protected:
    
    PurchaseDelegate* m_delegate;
};


#endif
