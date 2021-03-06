import BuildDeploy from '../components/BuildDeploy';
import ContextList from '../components/ContextList';
import ContextMonitor from '../components/ContextMonitor';
import ApiManager from '../components/ApiManager';
import ApiDetails from '../components/ApiDetails';
import ContextEdit from '../components/ContextEdit';
import ContextDeployment from '../components/ContextDeployment';
import PodManager from "../components/pod/PodManager";
import FlowManager from "../components/FlowManager";

const routers = [{
    path: '/',
    component: ContextList
}, {
    path: '/deploy',
    component: BuildDeploy
}, {
    path: '/monitor',
    component: ContextMonitor
}, {
    path: '/apiManager',
    component: ApiManager
}, {
    path: '/apiDetails',
    component: ApiDetails
}, {
    path: '/contextEdit',
    component: ContextEdit
}, {
    path: '/contextDeployment',
    component: ContextDeployment
}, {
    path: '/pod-manager',
    component: PodManager
}, {
    path:'/flowManager',
    component: FlowManager
}];
export default routers;