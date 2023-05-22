'use client';

import React, { useEffect, useState } from 'react'
import styles from './page.module.css'
import Link from 'next/link';


export default function Page() {
    const [username, setUsername] = useState<any>(localStorage.getItem('username')?.replaceAll('"', ''));
    const [password, setPassword] = useState<any>(localStorage.getItem('password')?.replaceAll('"', ''));
    const [completedOrders, setCompletedOrders] = useState<any>([]);
    const [fee, setFee] = useState<any>('');

    const auth = 'Basic ' + btoa(username + ':' + password);


    useEffect(() => getNumberOfCompletedOrders() , []);


    const getNumberOfCompletedOrders = () => {
        fetch('http://localhost:8080/Al-Akeel/api/runner/completedOrders', {
            method: 'GET',
            headers: {
                accept: '*/*',
                'Content-Type': 'application/json',
                authorization: auth
            },
        })
            .then((response) => response.json())
            .then((data) => {
                setCompletedOrders(data);
            }
            );
    }


    const handleCompleteOrder = (e: any) => {
        fetch('http://localhost:8080/Al-Akeel/api/runner/completeOrder', {
            method: 'GET',
            headers: {
                accept: '*/*',
                authorization: auth,
            },
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                getNumberOfCompletedOrders();
            }
            );
    }


    const handleSubmit = (e: any) => {
        e.preventDefault();
        console.log(fee);
        var url = 'http://localhost:8080/Al-Akeel/api/runner/fees?new_fees=' + fee;
        fetch(url, {
            method: 'POST',
            headers: {
                accept: '*/*',
                authorization: auth,
                'Content-Type': 'application/json',
            },
            // body: JSON.stringify({new_fees: fee})
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
            }
            );
    }


    return (
        <main>
            <nav className={styles.center}>
                <Link href="/">
                    Al-Akeel | Home
                </Link>{' '}
            </nav>
            <div className={styles.main}>
                <h1 className={styles.h1}>Runner Account</h1>
                <br></br>
                <h2>Number of completed Orders: {completedOrders}</h2>
                <br></br>
                <button className={styles.submit} type='submit' onClick={handleCompleteOrder}>Complete Order</button>
            </div>
            <div className={styles.main2}>
                <form className={styles.form}>
                    <label className={styles.label}>Change fee:</label>
                    <br></br>
                    <input className={styles.input} type="text" name="fee" required value={fee} onChange={(e) => setFee(e.target.value)} />
                    <br></br>
                    <button className={styles.submit} type="submit" onClick={handleSubmit} >Submit</button>
                </form>
            </div>
        </main>
    )
}
