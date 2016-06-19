
#ifndef X_RUN_LOOP_H
#define X_RUN_LOOP_H
#include <list>
#include <mutex>
using namespace std;
#include "cocos2d.h"
using namespace cocos2d;

class  RunLoopObserver
{
public:

	virtual void operate(){}

	virtual ~RunLoopObserver(){}
};

class RunLoop : public Node
{
public:

	static RunLoop* getInstance();

	void addObserver(RunLoopObserver* observer);

protected:

	RunLoop();

	void dispatchObserver(float );

	RunLoopObserver* front();

	void pop();

protected:

	static RunLoop* s_instance;

protected:

	std::mutex	m_mutext;

	std::list<RunLoopObserver*> m_queueObserver;

};

#endif
