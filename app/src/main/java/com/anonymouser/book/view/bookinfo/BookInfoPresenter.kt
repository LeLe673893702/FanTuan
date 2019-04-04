package com.anonymouser.book.view.bookinfo

import android.text.TextUtils
import com.anonymouser.book.bean.SearchBookInfoBean
import com.anonymouser.book.bean.ZhuiShuSearcheBean
import com.anonymouser.book.utlis.http.ServiceApi
import com.lzy.okgo.model.Response
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import org.greenrobot.eventbus.EventBus
import java.net.URLDecoder
import java.util.*

/**
 *
 * @what
 * @author 17173
 * @date 2019/4/3
 *
 */
class BookInfoPresenter(private val bookName : String, private val mView: BookInfoContract.View) : BookInfoContract.Presenter {
    override fun loadData() {
        if(TextUtils.isEmpty(bookName)) {
            mView.loadFailed()
            return
        }
        Observable
                .zip(ServiceApi.searchBookInfo(bookName), ServiceApi.zhuishuSearch(bookName), BiFunction<Response<Array<SearchBookInfoBean>>, Response<ZhuiShuSearcheBean>, ArrayList<SearchBookInfoBean>> { response, zhuiShuSearcheBeanResponse ->
                    val searchBookInfoBeans = ArrayList<SearchBookInfoBean>()
                    //把追书的结果转换成 SearchBookInfoBean
                    var zhuishuBookInfoBean: SearchBookInfoBean
                    for (bean in zhuiShuSearcheBeanResponse.body().books) {
                        zhuishuBookInfoBean = SearchBookInfoBean()
                        zhuishuBookInfoBean.bookName = bean.title
                        zhuishuBookInfoBean.img = URLDecoder.decode(bean.cover).replace("/agent/", "")
                        zhuishuBookInfoBean.author = bean.author
                        zhuishuBookInfoBean.intro = bean.shortIntro
                        zhuishuBookInfoBean.id = bean._id
                        zhuishuBookInfoBean.type = bean.cat
                        zhuishuBookInfoBean.tag = "ZS"
                        zhuishuBookInfoBean.isZhuiShu = true
                        searchBookInfoBeans.add(zhuishuBookInfoBean)
                    }
                    searchBookInfoBeans.addAll(Arrays.asList(*response.body()))

                    //把可能没有源的追书结果移动到后面
                    var size = zhuiShuSearcheBeanResponse.body().books.size

                    for (i in 0 until size) {
                        if (searchBookInfoBeans[i].img.contains("qidian")) {
                            searchBookInfoBeans.add(searchBookInfoBeans[i])
                            searchBookInfoBeans.removeAt(i)
                            size--
                        }
                    }

                    searchBookInfoBeans
                })
                .subscribe(object : Observer<ArrayList<SearchBookInfoBean>> {
                    override fun onSubscribe(@NonNull d: Disposable) {

                    }

                    override fun onNext(@NonNull searchBookInfoBeen: ArrayList<SearchBookInfoBean>) {
                        if (searchBookInfoBeen.isNotEmpty()) {
                            mView.loadSucceed(searchBookInfoBeen[0])
                        }
                    }

                    override fun onError(@NonNull e: Throwable) {
                        Logger.e(e.toString())
                        mView.loadFailed()
                    }

                    override fun onComplete() {

                    }
                })
    }

    override fun onDestroy() {
    }

}